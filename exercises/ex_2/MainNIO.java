package exercises;
import aux_tools.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.channels.*;
import java.util.*;

public class MainNIO {
    private static final String RECEIVER = "./classes/assets/receiver.txt";
    private static final String SOURCE = "./classes/assets/source.txt";
    private static final String GEN_COLOR = "cyan";

    private static String read(String filePath) throws IOException {
        String result = "";
        RandomAccessFile file = new RandomAccessFile(filePath, "r");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(512);

        while (channel.read(buffer) > 0) {
            buffer.flip();

            while (buffer.hasRemaining()) {
                result += (char)buffer.get();
            }
        }

        return result;
    }

    private static void write(String data) throws IOException {
        Path path = Paths.get(RECEIVER);

        Set<StandardOpenOption> options = new HashSet<>();
        options.add(StandardOpenOption.CREATE);
        options.add(StandardOpenOption.WRITE);
        FileChannel channel = FileChannel.open(path, options);

        channel.write(ByteBuffer.wrap(data.getBytes()));
        channel.close();
    }

	public static void help() {
		Output.println("HELLO HELP!");
	}

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws MainException, IOException, Exception {
        Output.print("This data from source: ");
        Output.print(read(SOURCE), GEN_COLOR);

        Output.print("This data from receiver, before pushing new data in it: ");
        Output.print(read(RECEIVER), GEN_COLOR);

        write(read(SOURCE));
        Output.print("This data from receiver, after pushing new data in it: ");
        Output.print(read(RECEIVER), GEN_COLOR);
    }
}

