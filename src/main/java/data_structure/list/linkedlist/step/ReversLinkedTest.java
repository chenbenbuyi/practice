package data_structure.list.linkedlist.step;

/**
 * @date: 2021/7/15 16:15
 * @author: chen
 * @desc: 翻转链表测试
 */
public class ReversLinkedTest {

    public static void main(String[] args) {
        StepLinked link = new StepLinked();
        link.add(new Step("初始化步骤1", "init/step1"));
        link.add(new Step("初始化步骤2", "init/step2"));
        link.add(new Step("初始化步骤3", "init/step3"));
        link.add(new Step("初始化步骤4", "init/step4"));
        link.add(new Step("初始化步骤5", "init/step5"));
        System.out.println(link.allStep());
        System.out.println(reversLinked(link.getStep(5),null));
    }


    public static Step reversLinked(Step step,Step pre){
        //当前节点为空，则直接返回指向前一节点的指针
        if(step == null) {
            return pre;
        }
        //备份当前节点的next指针
        Step next = step.getNext();
        //将当前节点的next指针指向前一节点
        step.next = pre;
        //递归调用方法，此时当前节点为原当前节点的下一个节点，而前一节点为原当前节点
        return reversLinked(next, step);
    }
}


