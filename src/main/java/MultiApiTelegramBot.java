import interfaces.BotModule;
import modules.HelloModule;
import modules.HelpModule;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class MultiApiTelegramBot extends TelegramLongPollingBot {

    private final String BOT_USERNAME = "@BotTurboCoinBot";
    private final ArrayList<BotModule> modules;

    public MultiApiTelegramBot(ArrayList<BotModule> modules) {
        super("6757307172:AAF7lN_WnUyk3UODX2nlXHnuWK83A9H2fp8");
        this.modules = modules;
    }

    @Override
    public void onUpdateReceived(Update update) {

        PartialBotApiMethod<?> response;

        BotModule module = this.modules.parallelStream()
                .filter(
                        (m) -> m.isFired(update.getMessage().getText()) || m.isActive()
                )
                .findFirst()
                .orElseGet(() -> {
                    BotModule m = new HelpModule();
                    return m;
                });

        response = module.handleCommand(update);

        try {
            if(response instanceof SendPhoto){
                execute((SendPhoto) response);
            }else if(response instanceof SendDocument){
                execute((SendDocument) response);
            }else if(response instanceof SendAudio){
                execute((SendAudio) response);
            }else if(response instanceof SendSticker){
                execute((SendSticker) response);
            }else if(response instanceof SendVideo){
                execute((SendVideo) response);
            }else {
                execute((BotApiMethod<?>) response);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return this.BOT_USERNAME;
    }
}
