package com.gym.app.service.audit.gymClass;

import com.gym.app.service.audit.gymClass.events.GetGymClassAttendanceEvent;
import com.gym.app.service.audit.gymClass.events.NewGymClassEvent;
import com.gym.app.service.audit.gymClass.events.SignUpToGymClassEvent;
import com.gym.app.service.dto.GymClassDTO;
import com.gym.app.service.dto.GymClassMemberPostDTO;
import com.gym.app.service.dto.GymClassPostDTO;
import com.gym.app.service.exceptions.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@Aspect
public class AuditAspect {
    @Autowired
    private ApplicationContext applicationContext;

    @Pointcut("execution(* com.gym.app.service.GymClassService.addGymClass(..))")
    public void anyAddGymClass() {
    }

    @Pointcut("execution(* com.gym.app.service.GymClassService.getGymClass(..))")
    public void anyGetGymClass() {
    }

    @Pointcut("execution(* com.gym.app.service.GymClassService.signupMember(..))")
    public void anySignupMember() {
    }

    @Before("anyAddGymClass()")
    public void logAddGymClass(JoinPoint joinPoint) {
        Object[] methodArgs = joinPoint.getArgs();
        int gymClassTypeId = ((GymClassPostDTO) methodArgs[0]).getGymClassTypeId();
        LocalDateTime gymClassStartTime = ((GymClassPostDTO) methodArgs[0]).getStartTime();

        applicationContext.publishEvent(new NewGymClassEvent(gymClassTypeId, gymClassStartTime));
    }

    @Around("anyGetGymClass()")
    public Object logGetGymClass(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] methodArgs = joinPoint.getArgs();
        int gymClassTypeId = (int) methodArgs[0];
        LocalDateTime gymClassStartTime = (LocalDateTime) methodArgs[1];

        Object result = joinPoint.proceed();
        Optional<GymClassDTO> optionalGymClass = (Optional<GymClassDTO>) result;

        optionalGymClass.ifPresent(
                gymClassDTO -> applicationContext.publishEvent(
                        new GetGymClassAttendanceEvent(gymClassTypeId, gymClassStartTime,
                                gymClassDTO.getMembers().size())));

        return result;
    }

    @Around("anySignupMember()")
    public Object logSignupMember(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] methodArgs = joinPoint.getArgs();
        int gymClassTypeId = ((GymClassMemberPostDTO) methodArgs[0]).getGymClassTypeId();
        LocalDateTime gymClassStartTime = ((GymClassMemberPostDTO) methodArgs[0]).getStartTime();
        int memberId = ((GymClassMemberPostDTO) methodArgs[0]).getMemberId();

        Object result;

        try {
            result = joinPoint.proceed();

            applicationContext.publishEvent(new SignUpToGymClassEvent(gymClassTypeId,
                    gymClassStartTime, memberId,
                    SignUpToGymClassEvent.SignUpToGymClassEventResult.SUCCESS));
        } catch (BadGymClassException e) {
            applicationContext.publishEvent(new SignUpToGymClassEvent(gymClassTypeId,
                    gymClassStartTime, memberId,
                    SignUpToGymClassEvent.SignUpToGymClassEventResult.GYM_CLASS_NOT_FOUND));

            throw e;
        } catch (GymClassFullCapacityException e) {
            applicationContext.publishEvent(new SignUpToGymClassEvent(gymClassTypeId,
                    gymClassStartTime, memberId,
                    SignUpToGymClassEvent.SignUpToGymClassEventResult.GYM_CLASS_FULL));

            throw e;
        } catch (BadMemberIdException e) {
            applicationContext.publishEvent(new SignUpToGymClassEvent(gymClassTypeId,
                    gymClassStartTime, memberId,
                    SignUpToGymClassEvent.SignUpToGymClassEventResult.MEMBER_NOT_FOUND));

            throw e;
        } catch (MemberAlreadySignedUpException e) {
            applicationContext.publishEvent(new SignUpToGymClassEvent(gymClassTypeId,
                    gymClassStartTime, memberId,
                    SignUpToGymClassEvent.SignUpToGymClassEventResult.MEMBER_ALREADY_SIGNED_UP));

            throw e;
        } catch (MembershipExpiredException e) {
            applicationContext.publishEvent(new SignUpToGymClassEvent(gymClassTypeId,
                    gymClassStartTime, memberId,
                    SignUpToGymClassEvent.SignUpToGymClassEventResult.MEMBERSHIP_EXPIRED));

            throw e;
        }

        return result;
    }
}
