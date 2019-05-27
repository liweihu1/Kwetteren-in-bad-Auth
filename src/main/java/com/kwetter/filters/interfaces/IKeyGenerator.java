package com.kwetter.filters.interfaces;

import java.security.Key;
import java.util.UUID;

public interface IKeyGenerator {
    Key generateKey();

    UUID getIdFromToken(String token);
}
