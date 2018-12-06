package com.NitroReader.Command;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.PrintWriter;

public class SwitchLike {
    private Command flipUpLike;
    private Command flipDownLike;

    public SwitchLike(Command flipUpLike, Command flipDownLike){
        this.flipUpLike = flipUpLike;
        this.flipDownLike = flipDownLike;
    }


    public void flipUp(int userid, int mangaid, PrintWriter out) throws JsonProcessingException {
        flipUpLike.execute(userid, mangaid, out);
    }

    public void FlipDown(int userid, int mangaid, PrintWriter out) throws JsonProcessingException {
        flipDownLike.execute(userid, mangaid, out);
    }
}
