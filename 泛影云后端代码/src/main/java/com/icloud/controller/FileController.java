package com.icloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.icloud.controller.utils.R;
import com.icloud.domain.User;
import com.icloud.service.IUserService;
import com.icloud.service.impl.IUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.LambdaConversionException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private IUserService ius;

    @PostMapping("/upload/{phone}")
    public R uploadProcess(@RequestBody User user, @PathVariable String phone){
        String uids = user.getFile();
        String[] split = uids.split(",");
        String[] splits = split[8].split("/");
        String[] s1 = splits[12].split("\"");

        User user1 = new User();


//
        user1.setFile(splits[8]);
        user1.setDescription(splits[10]);


//        String filePath = "";
//        String format = sdf.format(new Date());
//        File folder = new File(fileSavePath + format);
//        if (!folder.isDirectory()){
//            folder.mkdirs();
//            String oldName = uploadFile.getOriginalFilename();
//            String newName = UUID.randomUUID().toString() +
//                    oldName.substring(oldName.lastIndexOf("."), oldName.length());
//            try {
//                uploadFile.transferTo(new File(folder, newName));
//                filePath = req.getScheme() + "://" + req.getServerName() + ":" +
//                        req.getServerPort() + "/uploadFile/" + format + newName;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return new R(true);
//            }
//        }
//        String oldName = uploadFile.getOriginalFilename();
//        String newName = UUID.randomUUID().toString() +
//                oldName.substring(oldName.lastIndexOf("."), oldName.length());
//
//        try {
//            uploadFile.transferTo(new File(folder, newName));
//            filePath = req.getScheme() + "://" + req.getServerName() + ":" +
//                    req.getServerPort() + "/uploadFile/" + format + newName;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new R(true);
//        }

        return new R(true,user1);
    }
    @PostMapping("/upload/s/{phone}")
    public R upload(@RequestBody User user, @PathVariable String phone){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone,phone);
        User one = ius.getOne(lqw);

        one.setFile(user.getFile());
        return new R(ius.update(one,lqw));
    }

    @GetMapping("/download/{phone}")
    public R getDicom(@PathVariable String phone){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone,phone);
        User one = ius.getOne(lqw);
        if (one.getFile()!=null&&one.getFile().length()!=0){
            return new R(true,one.getFile());
        }else {
            return new R(false);
        }
    }

//    @GetMapping("/download")
//    public ResponseEntity<InputStreamResource> download()throws Exception{
//        InputStreamResource isr = new InputStreamResource(new FileInputStream("E:\\uploadFile\\2023\\01\\09\\32165550-61fa-4fc9-9f93-70cea0f8d3e5.docx"));
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .header("Content-disposition", "attachment; filename = hjk.docx")
//                .body(isr);
//    }
}
