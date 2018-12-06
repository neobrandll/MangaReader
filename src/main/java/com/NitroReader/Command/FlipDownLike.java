package com.NitroReader.Command;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.PrintWriter;

public class FlipDownLike implements Command {
    private Like thelike;

    public FlipDownLike(Like like){
        this.thelike = like;
    }

    @Override
    public void execute(int userid, int mangaid, PrintWriter out) throws JsonProcessingException {
        thelike.off(userid, mangaid, out);
    }
}
