package azzy.fabric.lff;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.PriorityQueue;

public class LuminousFlux implements ModInitializer {

	Comparator<Test> sorter = Comparator.comparing(Test::getPriority);
	PriorityQueue<Test> queue = new PriorityQueue<>(16, sorter);
	Logger LFFlog = LogManager.getLogger("Luminous Flux Framework");

	@Override public void onInitialize() {

		queue.add(new Test(1));
		queue.add(new Test(2));
		queue.add(new Test(3));
		queue.add(new Test(4));
		queue.add(new Test(5));

		while(!queue.isEmpty()){
			LFFlog.error(queue.poll().priority);
		}
	}

	private class Test implements Comparable<Test>{

		int priority;

		public Test(int priority){
			this.priority = priority;
		}

		public int getPriority() {
			return priority;
		}

		@Override
		public int compareTo(Test o) {
			return priority;
		}
	}
}
