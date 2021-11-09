package io.github.duplexsystem.jzlibng;

import java.io.IOException;
import java.io.InputStream;

public class FastParallelGZIPInputStream extends FastGZIPInputStream {
    public FastParallelGZIPInputStream(InputStream in, int size) throws IOException {
        super(in, size);
    }

    public FastParallelGZIPInputStream(InputStream in) throws IOException {
        super(in);
    }
}
