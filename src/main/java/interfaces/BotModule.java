package interfaces;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

public abstract class BotModule {

    private final String startCommand; // comando che attiva questo modulo -> es /meteo

    // se e' active, allora tutti i comandi successivi verranno gestiti solo da questo modulo.
    private boolean isActive;

    public BotModule(String startCommand) {
        this.startCommand = startCommand;
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public boolean isFired(String issuedCommand) {
        return issuedCommand.startsWith(this.startCommand);
    }

    abstract public PartialBotApiMethod<?> handleCommand(Update update);

}
