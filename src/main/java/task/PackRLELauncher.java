package task;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.*;

public class PackRLELauncher {

    @Option(name = "-z", forbids = {"-u"}, metaVar = "Pack")
    private boolean pack;

    @Option(name = "-u", forbids = {"-z"}, metaVar = "Unpack")
    private boolean unpack;

    @Option(name = "-out", metaVar = "OutputName", required = true)
    private String outputName;

    @Argument(metaVar = "InputName", required = true)
    private String inputName;

    private boolean isFileEmpty(File file) {
        return file.length() == 0;
    }

    private void launch(String[] args) {

        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("pack-rle [-z|-u] [-out outputname.txt] inputname.txt");
        }

        if (new PackRLELauncher().isFileEmpty(new File(inputName))) {
            throw new IllegalArgumentException("Your file is empty");
        }

        String line;
        PackRLE packRLE = new PackRLE();

        try (BufferedReader input = new BufferedReader(new FileReader(inputName));
            BufferedWriter output = new BufferedWriter(new FileWriter(outputName));
            BufferedReader input2 = new BufferedReader(new FileReader(inputName))) {

            boolean flag = input2.readLine() == null;

            while ((line = input.readLine()) != null) {
                flag = input2.readLine() == null;
                if (pack) output.write(packRLE.pack(line));
                else output.write(packRLE.unpack(line));
                if (!flag) {
                    output.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new PackRLELauncher().launch(args);
    }
}