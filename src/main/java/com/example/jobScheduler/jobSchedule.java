package com.example.jobScheduler;


import com.example.entity.User;
import com.example.repository.UserRepo;
import com.example.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.Calendar;
import java.util.List;

@Component
@Slf4j
public class jobSchedule {

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailService emailService;
//    @Scheduled(fixedDelay = 5000)  //cu 5 giay thuc hien lai 1 lan
    @Scheduled(cron="0 0 9 * * *") //9h sáng mỗi ngày
    public void hello(){
        log.info("Hello");
    }

    @Scheduled(cron="0 0 9 * * *") //9h sáng mỗi ngày
    public void sendmsg(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        List<User> userList = userRepo.searchByBirthdate(day, month+1);
        for(User u: userList){
            log.info("Happy Birthday" + u.getName());
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void sendEmail(){
        emailService.testEmail();
    }
}
