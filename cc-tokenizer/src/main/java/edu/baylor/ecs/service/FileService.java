package edu.baylor.ecs.service;

import edu.baylor.ecs.models.MethodRepresentation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileService {

    String readFile(String filename) throws IOException {
        InputStream is = new FileInputStream(filename);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));

        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();

        while(line != null){
            if(!line.startsWith("import") && !line.startsWith("package")) {
                sb.append(line).append("\n");
            }
            line = buf.readLine();
        }

        return sb.toString();
    }

    void countLines(MethodRepresentation rep) {
        int fileLines = 0, linesOfCode = 0, logicalLinesOfCode = 0;
        String raw = rep.getRaw();
        for(String line : raw.split("\n")){
            fileLines++;
            if(!line.isEmpty()){
                line = line.strip();
                if(!line.startsWith("/") && !line.startsWith("*")){
                    linesOfCode++;
                    if(!line.equals("{") && !line.equals("}")){
                        logicalLinesOfCode++;
                    }
                }
            }
        }
        rep.setFileLines(fileLines);
        rep.setLinesOfCode(linesOfCode);
        rep.setLogicalLinesOfCode(logicalLinesOfCode);
    }

    public void hash(MethodRepresentation rep) {
        StringBuilder trimmedBuilder = new StringBuilder();
        StringBuilder bareBuilder = new StringBuilder();

        String raw = rep.getRaw();
        for(String line : raw.split("\n")){
            if(!line.isEmpty()){
                line = line.strip();
                if(!line.startsWith("/") && !line.startsWith("*")){
                    trimmedBuilder.append(line);
                    trimmedBuilder.append("\n");
                    if(!line.equals("{") && !line.equals("}")){
                        bareBuilder.append(line);
                        bareBuilder.append("\n");
                    }
                }
            }
        }

        String trimmed = trimmedBuilder.toString();
        String trimmedMd5 = DigestUtils.md5Hex(trimmed).toUpperCase();
        rep.setTrimmedHash(trimmedMd5);

        String bare = bareBuilder.toString();
        String bareMd5 = DigestUtils.md5Hex(bare).toUpperCase();
        rep.setBareHash(bareMd5);

        String fullMd5 = DigestUtils.md2Hex(raw).toUpperCase();
        rep.setFullHash(fullMd5);
    }
}
