package connection;

import data.CollectionManager;
import data.database.QueryExecutionException;
import logic.CommandsExecutor;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.logging.Logger;

public class MusicBandServer {
    private static final Logger logger = Logger.getLogger(MusicBandServer.class.getName());

    private final int port;
    private final CommandsReader commandsReader;
    private final CommandsExecutor commandsExecutor;
    private final CollectionManager collectionManager;

    public MusicBandServer(int port, CommandsExecutor commandsExecutor, CollectionManager collectionManager) {
        this.port = port;
        this.commandsReader = new CommandsReader();
        this.commandsExecutor = commandsExecutor;
        this.collectionManager = collectionManager;
    }

    public void launch() throws IOException, NoSuchAlgorithmException {
        logger.info("Launching the server ");

        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            int select = selector.select();
            if(select == 0){
                continue;
            }
            Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
            while (selectedKeys.hasNext()){
                checkSave();
                SelectionKey k = selectedKeys.next();
                try{
                    if(k.channel() == ssc){
                        SocketChannel channel = ssc.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                        logger.info("Client connected " + channel);
                        System.out.println("Client connected " + channel);
                    }
                    else{
                            MusicBandRequest command = commandsReader.readCommand((SocketChannel) k.channel());
                        try{
                                MusicBandResponse response = commandsExecutor.executeCommand(command);
                                ResponseSender.sendResponse(response, (SocketChannel) k.channel());
                            }
                            catch (StreamCorruptedException|EOFException ex){
                            logger.info("Client disconnected " + k.channel());
                            System.out.println("Client disconnected " + k.channel());
                            k.cancel();
                            }
                        catch (QueryExecutionException ex){
                            logger.info("Server error" + ex.getMessage());
                            MusicBandResponse response = new MusicBandResponse();
                            response.status = ResponseStatus.FAIL;
                            response.response = "Server error";
                            ResponseSender.sendResponse(response, (SocketChannel) k.channel());
                            }
                        }
                } catch (ClassNotFoundException | IOException e) {
                    logger.info("Client disconnected " + k.channel());
                    System.out.println("Client disconnected " + k.channel());
                    k.cancel();
                }
                finally {
                    selectedKeys.remove();
                }
            }
        }
    }

    private void checkSave() throws IOException {
        if(System.in.available() > 0){
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            if(reader.readLine().equals("save")){
                System.out.println("Save is no longer available!");
            }
        }
    }
}
