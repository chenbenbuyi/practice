package data_structure.list.linkedlist.step;

import java.util.ArrayList;
import java.util.List;

public class StepLinked {

	private Step step;

	public synchronized StepLinked add(Step step) {
		if (this.step == null) {
			step.setCode(1);
			this.step = step;
			return this;
		}
		
		Step tmp = this.step;
		while (tmp.getNext() != null) {
			tmp = tmp.getNext();
		}
		step.setCode(tmp.getCode() + 1);
		tmp.setNext(step);
		step.setPrev(tmp);
		return this;
	}


    public Step getFirstStep() {
        return step;
    }



    public Step getStep(int code) {
		if(step.getCode() == code) {
			return step;
		}
		Step tmp = step;
		while (tmp.getNext() != null) {
			tmp = tmp.getNext();
			if(tmp.getCode() == code) {
				return tmp;
			}
		}
		return null;
	}
	

	public List<Step> allStep(){
		List<Step> steps = new ArrayList<>();
		if(step == null) {
			return steps;
		}
		steps.add(step);
		Step tmp = step;
		while (tmp.getNext() != null) {
			tmp = tmp.getNext();
			steps.add(tmp);
		}
		return steps;
	}


}