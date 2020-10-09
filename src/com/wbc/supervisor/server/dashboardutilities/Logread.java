package com.wbc.supervisor.server.dashboardutilities;


import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Logread {
    public static void readServerLog(HttpServletResponse response, String filename, Logger logger ) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            Path path = Paths.get(filename);
            File logfile = path.toFile();
            if (logfile.exists()) {
                List<String> tail = tailFile( path, 1000);
                tail.forEach(writer::println);
            } else {
                writer.println("Logread: File does not exist > " + filename );
            }
        } catch (IOException e) {
            writer.println("readServerLog Exception: " + e.getMessage() );
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }


    public static final List<String> tailFile(final Path source, final int limit) throws IOException {

        try (Stream<String> stream = Files.lines(source)) {
            RingBuffer buffer = new RingBuffer(limit);
            stream.forEach(line -> buffer.collect(line));

            return buffer.contents();
        }

    }

    private static final class RingBuffer {
        private final int limit;
        private final String[] data;
        private int counter = 0;

        public RingBuffer(int limit) {
            this.limit = limit;
            this.data = new String[limit];
        }

        public void collect(String line) {
            data[counter++ % limit] = line;
        }

        public List<String> contents() {
            return IntStream.range(counter < limit ? 0 : counter - limit, counter)
                    .mapToObj(index -> data[index % limit])
                    .collect(Collectors.toList());
        }

    }



}
