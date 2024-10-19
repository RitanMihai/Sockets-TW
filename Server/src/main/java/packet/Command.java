package packet;

import java.io.Serializable;

public enum Command { /* implements Serializable is optional, enum is Serializable by default */
    LOGIN,
    REGISTER,
    MESSAGE_ALL,
    MESSAGE_INDIVIDUAL
}
