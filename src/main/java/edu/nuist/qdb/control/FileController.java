package edu.nuist.qdb.control;

import com.alibaba.fastjson.JSONObject;
import edu.nuist.qdb.entity.question.Question;
import edu.nuist.qdb.entity.question.QuestionAssemblor;
import edu.nuist.qdb.entity.question.QuestionService;
import edu.nuist.qdb.service.ajax.result.Result;
import edu.nuist.qdb.service.ajax.result.ResultMessage;
import edu.nuist.qdb.util.CheckFileFormatUtil;
import edu.nuist.qdb.util.FileUploader;
import edu.nuist.qdb.xlsutil.Cell;
import edu.nuist.qdb.xlsutil.XLSReader;
import javassist.runtime.Inner;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.FileStore;
import java.security.PrivateKey;
import java.util.LinkedList;
import java.util.List;

@CrossOrigin
@RestController
public class FileController {
    @Autowired
    private FileUploader fileUploader;
    @Autowired
    private QuestionService questionService;


    @PostMapping("/upload")

    public String upload(@RequestParam() MultipartFile file) {


        JSONObject obj = (JSONObject) fileUploader.singel(file, "xlshaha", "all").getObj();
        String filePath = (String) obj.get("inner");

        try {
            batchSave(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;

    }


    private List<Question> batchSave(String filePath) throws Exception {
        QuestionAssemblor qAssemblor = new QuestionAssemblor();
        XLSReader rr = new XLSReader("e:/xlshaha/" + filePath);
        List<List<Cell>> table = rr.read(0);
        List<Question> questions = new LinkedList<>();

        int index = 0;
        for (List<Cell> row : table) {
            try {
                if (index == 0) {
                    index++;
                    continue;
                }
                System.out.println(row);
                Question q = qAssemblor.exec(row);
                questionService.save(q);
                questions.add(q);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return questions;
    }


}

