package uz.pdp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class UploadFile2Dto {
    private String title;
    private String desc;
    private MultipartFile file;
}
