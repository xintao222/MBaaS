package com.angkorteam.mbaas.server.nashorn.wicket.protocol.ws.api;

import com.angkorteam.mbaas.server.nashorn.Disk;
import com.angkorteam.mbaas.server.nashorn.Factory;
import com.angkorteam.mbaas.server.wicket.ApplicationUtils;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.protocol.ws.api.WebSocketBehavior;
import org.apache.wicket.protocol.ws.api.WebSocketRequestHandler;
import org.apache.wicket.protocol.ws.api.message.*;
import org.apache.wicket.request.cycle.RequestCycle;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by socheat on 7/2/16.
 */
public class NashornWebSocketBehavior extends WebSocketBehavior {

    private String script;

    private Factory factory;

    private Disk disk;

    private Map<String, Object> pageModel;

    private String applicationCode;

    public NashornWebSocketBehavior(Factory factory, Disk disk, String script, Map<String, Object> pageModel, String applicationCode) {
        this.factory = factory;
        this.pageModel = pageModel;
        this.disk = disk;
        this.script = script;
        this.applicationCode = applicationCode;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        ScriptEngine engine = ApplicationUtils.getApplication().getScriptEngine();
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            throw new WicketRuntimeException(e);
        }
        Invocable invocable = (Invocable) engine;
        {
            IScriptMessage scriptMessage = invocable.getInterface(IScriptMessage.class);
            if (scriptMessage == null) {
                throw new WicketRuntimeException("function script_on_message(requestCycle, disk, jdbcTemplate, factory, pageModel, event, message){} is missing");
            }
            JdbcTemplate jdbcTemplate = ApplicationUtils.getApplication().getJdbcTemplate(this.applicationCode);
            StringBuilder body = scriptMessage.script_on_message(RequestCycle.get(), this.disk, jdbcTemplate, this.factory, this.pageModel, "event", "message");
            String scriptOnMessage = "Wicket.Event.subscribe('/websocket/message', function(event, message){ " + (body == null || "undefined".equals(body.toString()) ? "" : body.toString()) + " });";
            response.render(OnDomReadyHeaderItem.forScript(scriptOnMessage));
        }
        {
            IScriptOpen scriptOpen = invocable.getInterface(IScriptOpen.class);
            if (scriptOpen == null) {
                throw new WicketRuntimeException("function script_on_open(requestCycle, disk, jdbcTemplate, factory, pageModel, event){} is missing");
            }
            JdbcTemplate jdbcTemplate = ApplicationUtils.getApplication().getJdbcTemplate(this.applicationCode);
            StringBuilder body = scriptOpen.script_on_open(RequestCycle.get(), this.disk, jdbcTemplate, this.factory, this.pageModel, "event");
            String scriptOnOpen = "Wicket.Event.subscribe('/websocket/open', function(event){" + (body == null || "undefined".equals(body.toString()) ? "" : body.toString()) + "});";
            response.render(OnDomReadyHeaderItem.forScript(scriptOnOpen));
        }
        {
            IScriptClosed scriptClosed = invocable.getInterface(IScriptClosed.class);
            if (scriptClosed == null) {
                throw new WicketRuntimeException("function script_on_closed(requestCycle, disk, jdbcTemplate, factory, pageModel, event){} is missing");
            }
            JdbcTemplate jdbcTemplate = ApplicationUtils.getApplication().getJdbcTemplate(this.applicationCode);
            StringBuilder body = scriptClosed.script_on_closed(RequestCycle.get(), this.disk, jdbcTemplate, this.factory, this.pageModel, "event");
            String scriptOnClosed = "Wicket.Event.subscribe('/websocket/closed', function(event){" + (body == null || "undefined".equals(body.toString()) ? "" : body.toString()) + "});";
            response.render(OnDomReadyHeaderItem.forScript(scriptOnClosed));
        }
        {
            IScriptError scriptError = invocable.getInterface(IScriptError.class);
            if (scriptError == null) {
                throw new WicketRuntimeException("function script_on_error(requestCycle, disk, jdbcTemplate, factory, pageModel, event){} is missing");
            }
            JdbcTemplate jdbcTemplate = ApplicationUtils.getApplication().getJdbcTemplate(this.applicationCode);
            StringBuilder body = scriptError.script_on_error(RequestCycle.get(), this.disk, jdbcTemplate, this.factory, this.pageModel, "event");
            String scriptOnError = "Wicket.Event.subscribe('/websocket/error', function(event){" + (body == null || "undefined".equals(body.toString()) ? "" : body.toString()) + "});";
            response.render(OnDomReadyHeaderItem.forScript(scriptOnError));
        }
    }

    @Override
    protected void onPush(WebSocketRequestHandler handler, IWebSocketPushMessage message) {
        ScriptEngine engine = ApplicationUtils.getApplication().getScriptEngine();
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            throw new WicketRuntimeException(e);
        }
        Invocable invocable = (Invocable) engine;
        ISocketPush push = invocable.getInterface(ISocketPush.class);
        if (push == null) {
            throw new WicketRuntimeException("function socket_on_push(requestCycle, disk, jdbcTemplate, factory, pageModel, handler, message){} is missing");
        }
        JdbcTemplate jdbcTemplate = ApplicationUtils.getApplication().getJdbcTemplate(this.applicationCode);
        push.socket_on_push(RequestCycle.get(), this.disk, jdbcTemplate, this.factory, this.pageModel, handler, message);
    }

    @Override
    protected void onConnect(ConnectedMessage message) {
        ScriptEngine engine = ApplicationUtils.getApplication().getScriptEngine();
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            throw new WicketRuntimeException(e);
        }
        Invocable invocable = (Invocable) engine;
        ISocketConnect connect = invocable.getInterface(ISocketConnect.class);
        if (connect == null) {
            throw new WicketRuntimeException("function socket_on_connect(requestCycle, disk, jdbcTemplate, factory, pageModel, message){} is missing");
        }
        JdbcTemplate jdbcTemplate = ApplicationUtils.getApplication().getJdbcTemplate(this.applicationCode);
        connect.socket_on_connect(RequestCycle.get(), this.disk, jdbcTemplate, this.factory, this.pageModel, message);
    }

    @Override
    protected void onClose(ClosedMessage message) {
        ScriptEngine engine = ApplicationUtils.getApplication().getScriptEngine();
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            throw new WicketRuntimeException(e);
        }
        Invocable invocable = (Invocable) engine;
        ISocketClose close = invocable.getInterface(ISocketClose.class);
        if (close == null) {
            throw new WicketRuntimeException("function socket_on_close(requestCycle, disk, jdbcTemplate, factory, pageModel, message){} is missing");
        }
        JdbcTemplate jdbcTemplate = ApplicationUtils.getApplication().getJdbcTemplate(this.applicationCode);
        close.socket_on_close(RequestCycle.get(), this.disk, jdbcTemplate, this.factory, this.pageModel, message);
    }

    @Override
    protected void onError(WebSocketRequestHandler handler, ErrorMessage message) {
        ScriptEngine engine = ApplicationUtils.getApplication().getScriptEngine();
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            throw new WicketRuntimeException(e);
        }
        Invocable invocable = (Invocable) engine;
        ISocketError error = invocable.getInterface(ISocketError.class);
        if (error == null) {
            throw new WicketRuntimeException("function socket_on_error(requestCycle, disk, jdbcTemplate, factory, pageModel, handler, message){} is missing");
        }
        JdbcTemplate jdbcTemplate = ApplicationUtils.getApplication().getJdbcTemplate(this.applicationCode);
        error.socket_on_error(RequestCycle.get(), this.disk, jdbcTemplate, this.factory, this.pageModel, handler, message);
    }

    @Override
    protected void onAbort(AbortedMessage message) {
        ScriptEngine engine = ApplicationUtils.getApplication().getScriptEngine();
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            throw new WicketRuntimeException(e);
        }
        Invocable invocable = (Invocable) engine;
        ISocketAbort abort = invocable.getInterface(ISocketAbort.class);
        if (abort == null) {
            throw new WicketRuntimeException("function socket_on_abort(requestCycle, disk, jdbcTemplate, factory, pageModel, message){} is missing");
        }
        JdbcTemplate jdbcTemplate = ApplicationUtils.getApplication().getJdbcTemplate(this.applicationCode);
        abort.socket_on_abort(RequestCycle.get(), this.disk, jdbcTemplate, this.factory, this.pageModel, message);
    }

    @Override
    protected void onMessage(WebSocketRequestHandler handler, TextMessage message) {
        ScriptEngine engine = ApplicationUtils.getApplication().getScriptEngine();
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            throw new WicketRuntimeException(e);
        }
        Invocable invocable = (Invocable) engine;
        ISocketTextMessage textMessage = invocable.getInterface(ISocketTextMessage.class);
        if (textMessage == null) {
            throw new WicketRuntimeException("function socket_on_text_message(requestCycle, disk, jdbcTemplate, factory, pageModel, handler, message){} is missing");
        }
        JdbcTemplate jdbcTemplate = ApplicationUtils.getApplication().getJdbcTemplate(this.applicationCode);
        textMessage.socket_on_text_message(RequestCycle.get(), this.disk, jdbcTemplate, this.factory, this.pageModel, handler, message);
    }

    @Override
    protected void onMessage(WebSocketRequestHandler handler, BinaryMessage message) {
        ScriptEngine engine = ApplicationUtils.getApplication().getScriptEngine();
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            throw new WicketRuntimeException(e);
        }
        Invocable invocable = (Invocable) engine;
        ISocketBinaryMessage binaryMessage = invocable.getInterface(ISocketBinaryMessage.class);
        if (binaryMessage == null) {
            throw new WicketRuntimeException("function socket_on_binary_message(requestCycle, disk, jdbcTemplate, factory, pageModel, handler, message){} is missing");
        }
        JdbcTemplate jdbcTemplate = ApplicationUtils.getApplication().getJdbcTemplate(this.applicationCode);
        binaryMessage.socket_on_binary_message(RequestCycle.get(), this.disk, jdbcTemplate, this.factory, this.pageModel, handler, message);
    }

    public interface IScriptOpen extends Serializable {
        StringBuilder script_on_open(RequestCycle requestCycle, Disk disk, JdbcTemplate jdbcTemplate, Factory factory, Map<String, Object> pageModel, String event);
    }

    public interface IScriptMessage extends Serializable {
        StringBuilder script_on_message(RequestCycle requestCycle, Disk disk, JdbcTemplate jdbcTemplate, Factory factory, Map<String, Object> pageModel, String event, String message);
    }

    public interface IScriptClosed extends Serializable {
        StringBuilder script_on_closed(RequestCycle requestCycle, Disk disk, JdbcTemplate jdbcTemplate, Factory factory, Map<String, Object> pageModel, String event);
    }

    public interface IScriptError extends Serializable {
        StringBuilder script_on_error(RequestCycle requestCycle, Disk disk, JdbcTemplate jdbcTemplate, Factory factory, Map<String, Object> pageModel, String event);
    }

    public interface ISocketPush extends Serializable {
        void socket_on_push(RequestCycle requestCycle, Disk disk, JdbcTemplate jdbcTemplate, Factory factory, Map<String, Object> pageModel, WebSocketRequestHandler handler, IWebSocketPushMessage message);
    }

    public interface ISocketConnect extends Serializable {
        void socket_on_connect(RequestCycle requestCycle, Disk disk, JdbcTemplate jdbcTemplate, Factory factory, Map<String, Object> pageModel, ConnectedMessage message);
    }

    public interface ISocketClose extends Serializable {
        void socket_on_close(RequestCycle requestCycle, Disk disk, JdbcTemplate jdbcTemplate, Factory factory, Map<String, Object> pageModel, ClosedMessage message);
    }

    public interface ISocketError extends Serializable {
        void socket_on_error(RequestCycle requestCycle, Disk disk, JdbcTemplate jdbcTemplate, Factory factory, Map<String, Object> pageModel, WebSocketRequestHandler handler, ErrorMessage message);
    }

    public interface ISocketAbort extends Serializable {
        void socket_on_abort(RequestCycle requestCycle, Disk disk, JdbcTemplate jdbcTemplate, Factory factory, Map<String, Object> pageModel, AbortedMessage message);
    }

    public interface ISocketTextMessage extends Serializable {
        void socket_on_text_message(RequestCycle requestCycle, Disk disk, JdbcTemplate jdbcTemplate, Factory factory, Map<String, Object> pageModel, WebSocketRequestHandler handler, TextMessage message);
    }

    public interface ISocketBinaryMessage extends Serializable {
        void socket_on_binary_message(RequestCycle requestCycle, Disk disk, JdbcTemplate jdbcTemplate, Factory factory, Map<String, Object> pageModel, WebSocketRequestHandler handler, BinaryMessage binaryMessage);
    }

}
