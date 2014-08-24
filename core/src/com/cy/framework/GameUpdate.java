package com.cy.framework;

public class GameUpdate
{
    // desired fps
    public final static int MAX_FPS = 50;
    // maximum number of frames to be skipped
    private final static int MAX_FRAME_SKIPS = 5;
    // the frame period
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    public void worldupdate()
    {
    }

    public void worldrender()
    {
    }

    public void update()
    {
		long beginTime = 0; // the time when the cycle begun
		long timeDiff = 0; // the time it took for the cycle to execute
		int sleepTime = 0; // ms to sleep (<0 if we're behind)
		int framesSkipped = 0; // number of frames being skipped
	
		beginTime = System.currentTimeMillis();
		// render state to the screen
		this.worldrender();
		// update game state
		this.worldupdate();
	
		// calculate how long did the cycle take
		timeDiff = System.currentTimeMillis() - beginTime;
		// calculate sleep time
		sleepTime = (int) (FRAME_PERIOD - timeDiff);
		if (sleepTime > 0)
		{
		    // if sleepTime > 0 we're OK
		    try
		    {
				// send the thread to sleep for a short period
				// very useful for battery saving
				Thread.sleep(sleepTime);
		    } catch (InterruptedException e)
		    {
		    }
		}
		while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS)
		{
		    // we need to catch up
		    // update without rendering
		    // add frame period to check if in next frame
		    this.worldupdate();
		    sleepTime += FRAME_PERIOD;
		    framesSkipped++;
		}
    }
}
