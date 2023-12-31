package ru.hogwarts.school.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.File;
import java.io.IOException;

public interface AvatarService {

    default void uploadAvatar(Long studentID,
                              MultipartFile avatarFile) throws IOException {

    }

    Avatar readFromDB(long id);

    File readFromFile(long id) throws IOException;

    Page<Avatar> getAllAvatars(Integer pageNo, Integer pageSize);
}
