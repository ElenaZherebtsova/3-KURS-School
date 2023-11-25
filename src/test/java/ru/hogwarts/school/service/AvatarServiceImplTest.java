package ru.hogwarts.school.service;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AvatarServiceImplTest {

    StudentService studentService = mock(StudentService.class);
    AvatarRepository avatarRepository = mock(AvatarRepository.class);
    String avatarsDir = "./src/test/resourses/avatar";
    AvatarServiceImpl avatarService = new AvatarServiceImpl(
            avatarsDir, studentService, avatarRepository);
    Student student = new Student(1L, "Garry", 12);

    @Test
    void uploadAvatar_shouldAvatarSavedToDBAndDirectory() throws IOException {
        String fileName = "11.pdf";
        MultipartFile file = new MockMultipartFile(fileName, fileName, "application/pdf", new byte[]{});
        when(studentService.read(student.getId())).thenReturn(student);
        when(avatarRepository.findByStudent_id(student.getId()))
                .thenReturn(Optional.empty());
        avatarService.uploadAvatar(student.getId(), file);
        Mockito.verify(avatarRepository, times(1)).save(any());
        // здесь у меня проблема: тест не запускается, тк видимо непрравильно импортировала метод VERUFY.
        // И вверху где импорт классов -- Java ругается...

        assertTrue(FileUtils.canRead(new File(avatarsDir + "/" + student.getId() + fileName + 1)));

    }

    @Test
    void readFromDB() {
    }

    @Test
    void readFromFile() {
    }
}