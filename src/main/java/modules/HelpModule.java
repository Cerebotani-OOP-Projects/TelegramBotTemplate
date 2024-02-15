package modules;

import config.BotConfiguration;
import interfaces.BotModule;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpModule extends BotModule {

    /**
     * Viene richiamato al comando /help e in caso di comando inesistente
     * */

    public HelpModule() {
        super("/help");
    }

    @Override
    public BotApiMethod<Message> handleCommand(Update update) {
        // TODO: stampare manuale / gestire errore ecc..
        SendMessage m = new SendMessage();
        m.setChatId(update.getMessage().getChatId());

        m.setText(BotConfiguration.HELP_TEXT);

        return m;
    }


}
