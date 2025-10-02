package com.vwth.challenge;

import com.vwth.challenge.application.SimulationService;
import com.vwth.challenge.domain.Robot;
import com.vwth.challenge.infrastructure.FileInputAdapter;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java -jar VWTH-Code-Challgenge.jar <inputfile>");
            return;
        }

        FileInputAdapter adapter = new FileInputAdapter();
        FileInputAdapter.SimulationInput input = adapter.loadFromFile(args[0]);

        SimulationService service = new SimulationService(input.floor());
        service.runMultipleRobots(
                input.robotCommands().stream()
                        .map(rc -> new SimulationService.RobotInstructions(rc.robot(), rc.commands()))
                        .toList());

        // Print final positions
        for (var rc : input.robotCommands()) {
            Robot r = rc.robot();
            System.out.printf("%d %d %s%n",
                    r.getX(),
                    r.getY(),
                    r.getDirection().toString().charAt(0));
        }
    }
}
