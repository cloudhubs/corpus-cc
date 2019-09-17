package edu.baylor.ecs.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DirectoryService {

    public List<String> getResourcePaths(String folderPath, int maxDepth, String extension){
        String directory = new File(folderPath).getAbsolutePath();
        Path start = Paths.get(directory);
        try {
            List<String> fileNames = Files.find(start, maxDepth, (path, attr) -> String.valueOf(path).toLowerCase().endsWith(extension))
                                        .sorted()
                                        .map(String::valueOf)
                                        .collect(Collectors.toList());
            System.out.println(fileNames);
            return fileNames;
        } catch(IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
