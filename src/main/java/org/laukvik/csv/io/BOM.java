package org.laukvik.csv.io;

/*
 * Copyright 2015 Laukviks Bedrifter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Constants indicating encoding used in text file
 *
 * @author Morten Laukvik
 */
public enum BOM {

    UTF32BE("utf-32be", (byte) 0x00, (byte) 0x00, (byte) 0xFE, (byte) 0xFF),
    UTF32LE("utf-32le", (byte) 0xFF, (byte) 0xFE, (byte) 0x00, (byte) 0x00),
    UTF16BE("utf-16be", (byte) 0xFE, (byte) 0xFF),
    UTF16LE("utf-16le", (byte) 0xFF, (byte) 0xFE),
    UTF8("utf-8", (byte) 0xEF, (byte) 0xBB, (byte) 0xBF);

    private final byte[] bytes;
    private final String charset;

    private BOM(String charset, byte... chars) {
        this.charset = charset;
        this.bytes = chars;
    }

    public Charset getCharset() {
        return Charset.forName(charset);
    }

    /**
     * Detects any encoding from file using BOM
     *
     * @param file
     * @return
     */
    public static BOM findBom(File file) {
        try (InputStream is = new FileInputStream(file)) {
            byte[] bytes = new byte[8];
            is.read(bytes, 0, 8);
            return BOM.parse(bytes);
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Parses and detects any encoding if present
     *
     * @param bytes
     * @return
     */
    public static BOM parse(byte... bytes) {
        for (BOM bom : values()) {
            if (bom.is(bytes)){
                return bom;
            }
        }
        return null;
    }

    public boolean is(final byte... bytes) {
        if (bytes.length < this.bytes.length){
            return false;
        }
        byte [] sameBytes = Arrays.copyOfRange(bytes, 0, this.bytes.length);
        return Arrays.equals( sameBytes, this.bytes);
    }

    public void write(final OutputStream out) throws IOException {
        out.write(getBytes());
    }

    public byte[] getBytes() {
        return bytes;
    }
}
