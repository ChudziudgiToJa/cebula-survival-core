package pl.cebula.smp.configuration.implementation;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

public class MessagesConfiguration extends OkaeriConfig {

    public Notice testMessage = Notice.builder()
            .actionBar("test")
            .build();
}
