package org.zerock.mreivew.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLDecoder;

@RestController
@Log4j2
public class UploadController {

    @PostMapping("/uploadAjax")
    public void uploadFile(MultipartFile[] uploadFiles) {

        for (MultipartFile uploadFile: uploadFiles) {
            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);

        }
    }

 //   @GetMapping("/display")
  //  public ResponseEntity<byte[]> getFile(String fileName, String size) {
 //       ResponseEntity<byte[]> result = null;

 //       try {
  //
  //          String srcFileName = URLDecoder.decode(fileName, "UTF-8");
//            File file = new File(uploadPath + File.separator+ srcFileName);

//            if
//        }
 //   }
}
