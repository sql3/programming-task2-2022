package task;

import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PackRLETest {

    @Test
    public void packTest() {
        PackRLE packRLE = new PackRLE();
        String input1 = packRLE.pack("kkkkkjjkkkjkkkk ABBBBCCCDFFFFFF");
        assertEquals("5k2j3kj4k A4B3CD6F", input1);

        String input2 = packRLE.pack("abcdEFG AABBCCDDD");
        assertEquals("abcdEFG 2A2B2C3D", input2);
    }

    @Test
    public void unpackTest() {
        PackRLE packRLE = new PackRLE();
        String input1 = packRLE.unpack("5k2j3kj4k A4B3CD6F");
        assertEquals("kkkkkjjkkkjkkkk ABBBBCCCDFFFFFF", input1);

        String input2 = packRLE.unpack("abcdEFG 2A2B2C3D");
        assertEquals("abcdEFG AABBCCDDD", input2);
    }

    @Test
    public void packContainsDigits() {
        PackRLE packRLE = new PackRLE();
        assertThrows(IllegalArgumentException.class, () -> {
            packRLE.pack("123412312");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            packRLE.pack("A2VD2GAGE56");
        });
    }

    @Test
    public void unpackContainsOnlyDigits() {
        PackRLE packRLE = new PackRLE();
        assertThrows(IllegalArgumentException.class, () -> {
            packRLE.unpack("123412312");
        });
    }

    @Test
    public void unpackHasLastDigit() {
        PackRLE packRLE = new PackRLE();
        assertThrows(IllegalArgumentException.class, () -> {
            packRLE.unpack("SDBS6DRG5EWR7");
        });
    }
}


class PackRLELauncherTest {

    @Test
    public void packTest() throws IOException {
        String cmd = "-z -out src/test/resources/testFiles/output1.txt src/test/resources/testFiles/input1.txt";
        String output = "src/test/resources/testFiles/output1.txt";
        String result = "src/test/resources/testFiles/result1.txt";

        PackRLELauncher.main(cmd.split(" "));

        assertEquals(-1, Files.mismatch(Path.of(output), Path.of(result)));
    }

    @Test
    public void unpackTest() throws IOException {
        String cmd = "-u -out src/test/resources/testFiles/output2.txt src/test/resources/testFiles/input2.txt";
        String output = "src/test/resources/testFiles/output2.txt";
        String result = "src/test/resources/testFiles/result2.txt";

        PackRLELauncher.main(cmd.split(" "));

        assertEquals(-1, Files.mismatch(Path.of(output), Path.of(result)));
    }

    @Test
    public void emptyFileTest() {
        String cmd = "-u -out src/test/resources/testFiles/output3.txt src/test/resources/testFiles/input3.txt";

        assertThrows(IllegalArgumentException.class, () -> {
            PackRLELauncher.main(cmd.split(" "));
        });
    }

    @Test
    public void packFileContainDigits() {
        String cmd = "-z -out src/test/resources/testFiles/output4.txt src/test/resources/testFiles/input4.txt";

        assertThrows(IllegalArgumentException.class, () -> {
            PackRLELauncher.main(cmd.split(" "));
        });
    }

    @Test
    public void unpackFileLastDigit() {
        String cmd = "-u -out src/test/resources/testFiles/output5.txt src/test/resources/testFiles/input5.txt";

        assertThrows(IllegalArgumentException.class, () -> {
            PackRLELauncher.main(cmd.split(" "));
        });
    }

    @Test
    public void wrongOptionsTest() {
        String cmd1 = "-u -z -out src/test/resources/testFiles/output5.txt src/test/resources/testFiles/input5.txt";
        String cmd2 = "-u -out src/test/resources/testFiles/output5.txt";
        String cmd3 = "-b -out src/test/resources/testFiles/output5.txt src/test/resources/testFiles/input5.txt";
        String cmd4 = "-u src/test/resources/testFiles/input5.txt";
        PackRLELauncher packRLELauncher = new PackRLELauncher();
        CmdLineParser cmdLineParser = new CmdLineParser(packRLELauncher);

        assertThrows(CmdLineException.class, () -> {
           cmdLineParser.parseArgument(cmd1.split(" "));
        });
        assertThrows(CmdLineException.class, () -> {
            cmdLineParser.parseArgument(cmd2.split(" "));
        });
        assertThrows(CmdLineException.class, () -> {
            cmdLineParser.parseArgument(cmd3.split(" "));
        });
        assertThrows(CmdLineException.class, () -> {
            cmdLineParser.parseArgument(cmd4.split(" "));
        });
    }
}