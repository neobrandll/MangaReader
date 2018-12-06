package com.NitroReader.Command;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.PrintWriter;

public interface Command {
    void execute(int userid, int mangaid, PrintWriter out) throws JsonProcessingException;
}
