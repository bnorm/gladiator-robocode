package bnorm.messages;

/**
 * The {@link MessageHandler} interface provides a simple way to require certain
 * methods pertaining to custom Robocode messages.
 * 
 * @author Brian Norman
 * @version 1.0
 */
public interface MessageHandler {

   /**
    * Handles the message that was specified. If the message handler does not
    * need this message than it is ignored.
    * 
    * @param message
    *           the message for the handler.
    */
   public void inMessage(Message message);

   /**
    * Handles the messages that are specified. If the message handler does not
    * need one of the messages specified than it is ignored. The easiest way to
    * implement this method is to call {@link #inMessage(Message)} for every
    * message in the collection.
    * 
    * @param messages
    *           the collection of messages for the handler.
    */
   public void inMessages(Iterable<Message> messages);

}
