package org.example.rest.auth.service;

import org.example.rest.auth.payload.request.AuthenticationRequest;
import org.example.rest.auth.payload.request.RegisterRequest;
import org.example.rest.auth.payload.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
