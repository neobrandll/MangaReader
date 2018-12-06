package com.NitroReader.Command;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.PrintWriter;

public class FlipUpLike implements Command {
    private Like thelike;

    public FlipUpLike(Like like){
        this.thelike = like;
    }

    @Override
    public void execute(int userid, int mangaid, PrintWriter out) throws JsonProcessingException {
        thelike.on(userid, mangaid, out);
    }
}
