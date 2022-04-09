package ru.geekbrains.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.nio.file.Path;

@RequiredArgsConstructor
@Setter
@Getter
public class PictureDto {

    private final String contentType;

    private final Path path;

    private byte[] data;
}