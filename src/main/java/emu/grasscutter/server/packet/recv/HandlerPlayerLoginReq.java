package emu.grasscutter.server.packet.recv;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.*;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.game.GameSession.SessionState;
import emu.grasscutter.server.packet.send.*;

@Opcodes(PacketOpcodes.PlayerLoginReq)
public class HandlerPlayerLoginReq extends PacketHandler {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        if (session.getAccount() == null) {
            session.close();
            return;
        }

        Player player = session.getPlayer();

        if (player.getAvatars().getAvatarCount() == 0) {
            session.setState(SessionState.PICKING_CHARACTER);
            session.send(new PacketDoSetPlayerBornDataNotify());
        } else {
            session.getPlayer().onLogin();
        }

        session.send(new PacketPlayerLoginRsp(session));
    }
}
