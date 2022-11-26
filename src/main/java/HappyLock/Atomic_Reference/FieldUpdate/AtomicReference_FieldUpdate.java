package HappyLock.Atomic_Reference.FieldUpdate;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

@Slf4j
public class AtomicReference_FieldUpdate {

    public static void main(String[] args) {

        Student student = new Student();

        AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(Student.class,String.class,"name");


        boolean update = updater.compareAndSet(student, null, "李四");

        log.debug("{}",update);

        log.debug("{}",student.getName());

        boolean update2 = updater.compareAndSet(student, null, "王五");

        log.debug("{}",update2);

        log.debug("{}",student.getName());


    }


}
