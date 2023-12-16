package uz.pdp.controller;

import org.springframework.core.io.PathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.dto.UploadFile2Dto;
import uz.pdp.dto.UploadFileDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/files")
public class FilesController {

    private static final String UPLOADS_PATH = "C:\\Users\\b.kambaraliev\\IdeaProjects\\g30\\spring\\g30-spring-security\\src\\main\\resources\\files\\uploads\\";

    @GetMapping("/upload")
    public String uploadPage() {
        return "uploads";
    }

//    @PostMapping("/upload")
//    public String uploadFile(@RequestParam(name = "file") MultipartFile[] files,
//                             @ModelAttribute UploadFileDto dto) throws IOException {
//        for (MultipartFile file : files) {
//            Files.copy(
//                    file.getInputStream(),
//                    Path.of(UPLOADS_PATH + file.getOriginalFilename()),
//                    StandardCopyOption.REPLACE_EXISTING
//            );
//        }
//        return "redirect:/files/upload";
//    }

    @PostMapping("/upload")
    public String uploadFile(@ModelAttribute UploadFile2Dto uploadFile2Dto, BindingResult errors) throws IOException {
        System.out.println(uploadFile2Dto);
        System.out.println(errors);

        Files.copy(
                uploadFile2Dto.getFile().getInputStream(),
                Path.of(UPLOADS_PATH + uploadFile2Dto.getFile().getOriginalFilename()),
                StandardCopyOption.REPLACE_EXISTING
        );
        return "redirect:/files/upload";
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable(name = "filename") String filename) throws IOException {
        PathResource resource = new PathResource(Path.of(UPLOADS_PATH).resolve(filename));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(resource.getFile().toPath())))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(resource.getContentAsByteArray());
    }
}
