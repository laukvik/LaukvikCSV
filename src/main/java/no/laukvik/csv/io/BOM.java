package no.laukvik.csv.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Constants indicating encoding used in text file.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Byte_order_mark">Byte Order Mark (wikipedia)</a>
 */
public enum BOM {

    /**
     * BOM for UTF32BE.
     */
    UTF32BE("utf-32be", (byte) 0x00, (byte) 0x00, (byte) 0xFE, (byte) 0xFF),
    /**
     * BOM for UTF32LE.
     */
    UTF32LE("utf-32le", (byte) 0xFF, (byte) 0xFE, (byte) 0x00, (byte) 0x00),
    /**
     * BOM for UTF16BE.
     */
    UTF16BE("utf-16be", (byte) 0xFE, (byte) 0xFF),
    /**
     * BOM for UTF16LE.
     */
    UTF16LE("utf-16le", (byte) 0xFF, (byte) 0xFE),
    /**
     * BOM for UTF8.
     */
    UTF8("utf-8", (byte) 0xEF, (byte) 0xBB, (byte) 0xBF);
    /**
     * Maximum BOM size.
     */
    private static final int MAX_BOM_SIZE = 8;
    /**
     * The bytes that recognizes the BOM.
     */
    private final byte[] bytes;
    /**
     * The associated Charset for the BOM.
     */
    private final String charset;

    /**
     * Creates a new BOM with the specified BOM and bytes.
     *
     * @param charsetName the charset
     * @param chars       the chars
     * @see Charset
     */
    BOM(final String charsetName, final byte... chars) {
        this.charset = charsetName;
        this.bytes = chars;
    }

    /**
     * Detects any encoding from file using BOM.
     *
     * @param file the file
     * @return the BOM
     */
    public static BOM findBom(final File file) {
        try (InputStream is = new FileInputStream(file)) {
            byte[] bytes = new byte[MAX_BOM_SIZE];
            is.read(bytes, 0, MAX_BOM_SIZE);
            return BOM.parse(bytes);
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * Parses and detects any BOM if present.
     *
     * @param bytes the bytes to check
     * @return the BOM found
     */
    private static BOM parse(final byte... bytes) {
        for (BOM bom : values()) {
            if (bom.is(bytes)) {
                return bom;
            }
        }
        return null;
    }

    /**
     * Finds the BOM by specifying its charset.
     *
     * @param charset the charset
     * @return the BOM
     */
    public static BOM findBomByCharset(final Charset charset) {
        for (BOM bom : values()) {
            if (bom.getCharset().equals(charset)) {
                return bom;
            }
        }
        return null;
    }

    /**
     * Returns the Charset.
     *
     * @return the Charset
     */
    public Charset getCharset() {
        return Charset.forName(charset);
    }

    /**
     * Returns whether the bytes matchesRow the current.
     *
     * @param values the bytes
     * @return true if it matchesRow
     */
    public boolean is(final byte... values) {
        if (values == null || values.length == 0 || values.length < this.bytes.length) {
            return false;
        }
        byte[] sameBytes = Arrays.copyOfRange(values, 0, this.bytes.length);
        return Arrays.equals(sameBytes, this.bytes);
    }

    /**
     * Returns the bytes for this BOM.
     *
     * @return the bytes
     */
    public byte[] getBytes() {
        return bytes;
    }
}
