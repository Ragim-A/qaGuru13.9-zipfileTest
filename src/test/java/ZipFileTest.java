import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.codeborne.pdftest.PDF;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ZipFileTest {
    @Test
    @DisplayName("Zip Files Test")
    void ZipTest() throws Exception {
        ZipFile zip = new ZipFile("zipTest.zip");

        ZipEntry csv = new ZipEntry("list.csv");
        try (InputStream csvStream = zip.getInputStream(csv)) {
            CSVReader reader = new CSVReader(new InputStreamReader(csvStream));
            List<String[]> list = reader.readAll();
            assertThat(list).contains(
                    new String[]{"model", "brand"},
                    new String[]{"vesta", "lada"},
                    new String[]{"a4", "audi"},
                    new String[]{"solaris", "hyundai"});
        }
        ZipEntry pdf = new ZipEntry("pdfsample.pdf");
        try (InputStream pdfStream = zip.getInputStream(pdf)) {
            PDF parsed = new PDF(pdfStream);
            assertThat(parsed.text).contains("Adobe Acrobat");
        }
        ZipEntry XlsEntry = zip.getEntry("sale.xlsx");
        try (InputStream xlsStream = zip.getInputStream(XlsEntry)) {
            XLS parsed = new XLS(xlsStream);
            assertThat(parsed.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue())
                    .isEqualTo("two");
        }
    }
}
