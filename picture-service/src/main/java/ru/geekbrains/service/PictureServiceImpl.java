package ru.geekbrains.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.repository.PictureRepository;
import ru.geekbrains.service.dto.PictureDto;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PictureServiceImpl implements PictureService {

    private static final Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);

    @Value("${picture.storage.path}")
    private String storagePath;

    private final PictureRepository pictureRepository;


    @Override
    public Optional<PictureDto> getPictureDataById(long id) {
        return pictureRepository.findById(id)
                .map(pic -> new PictureDto(pic.getContentType(), Paths.get(storagePath, pic.getStorageFileName())))
                .filter(pic -> Files.exists(pic.getPath()))
                .map(pic -> {
                    try {
                        pic.setData(Files.readAllBytes(pic.getPath()));
                        return pic;
                    } catch (IOException ex) {
                        logger.error("Can't read file", ex);
                        throw new RuntimeException(ex);
                    }
                });
    }

    @Override
    public String createPicture(byte[] pictureData) {
        String filename = UUID.randomUUID().toString();
        try (OutputStream os = Files.newOutputStream(Paths.get(storagePath, filename))) {
            os.write(pictureData);
        } catch (IOException ex) {
            logger.error("Can't write to file", ex);
            throw new RuntimeException(ex);
        }
        return filename;
    }
}
