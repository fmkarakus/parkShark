package com.switchfully.parkshark.service.allocation;

import com.switchfully.parkshark.domain.allocation.Allocation;
import com.switchfully.parkshark.domain.allocation.AllocationRepository;
import com.switchfully.parkshark.domain.allocation.AllocationStatus;
import com.switchfully.parkshark.domain.member.Member;
import com.switchfully.parkshark.service.allocation.DTO.AllocationDTO;
import com.switchfully.parkshark.service.allocation.DTO.StartAllocationDTO;
import com.switchfully.parkshark.service.allocation.DTO.StopAllocationDTO;
import com.switchfully.parkshark.service.member.MemberService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.time.LocalDateTime;

@Service
@Transactional
public class AllocationService {

    private final AllocationValidation allocationValidation;
    private final AllocationMapper allocationMapper;
    private final AllocationRepository allocationRepository;
    private final MemberService memberService;

    public AllocationService(AllocationValidation allocationValidation, AllocationMapper allocationMapper, AllocationRepository allocationRepository, MemberService memberService) {
        this.allocationValidation = allocationValidation;
        this.allocationMapper = allocationMapper;
        this.allocationRepository = allocationRepository;
        this.memberService = memberService;
    }


    public List<AllocationDTO> getAllAllocations() {
        return allocationMapper.mapAllocationToAllocationDTO(allocationRepository.findAll());
    }
    public List<AllocationDTO> getAllAllocationsFiltered(int limit, String status, String order) {

        List<Allocation> allocationList = allocationRepository.findAll();
        int resolvedLimit =setLimit(limit);

        if (order.equals("DESC")){
           Collections.reverse(allocationList);
        }
        if (status.isEmpty()){
            return allocationMapper.mapAllocationToAllocationDTO(allocationList.stream()
                    .limit(resolvedLimit)
                    .toList());
        }

        return allocationMapper.mapAllocationToAllocationDTO(allocationList.stream()
                .filter(allocation -> allocation.getStatus().name().equals(status))
                .limit(resolvedLimit)
                .toList()
        );
    }
    private int setLimit(int limit){
        if(limit<=0){
            return allocationRepository.findAll().size();
        }
        return limit;
    }

    public AllocationDTO createAllocation(StartAllocationDTO startAllocationDTO, String loggedInMember) {
        allocationValidation.validateAllocation(startAllocationDTO);
        Member member=memberService.findMemberById(startAllocationDTO.memberId());
        assertMemberHasAuthority(member.getEmail(),loggedInMember);
        Allocation allocation = allocationRepository.save(allocationMapper.mapStartAllocationDTOToAllocation(startAllocationDTO));
        allocation.decreaseParkingLotCapacity();
        return allocationMapper.mapAllocationToAllocationDTO(allocation);
    }

    public StopAllocationDTO stopAllocation(long allocationId, String loggedInMember) {
        Allocation allocation = getAllocationById(allocationId);
        assertTheAllocationIsActive(allocation);
        assertMemberHasAuthority(allocation.getMember().getEmail(),loggedInMember);
        allocation.setStoppingTime(LocalDateTime.now());
        allocation.setStatus(AllocationStatus.STOPPED);
        allocation.increaseParkingLotCapacity();
        return allocationMapper.mapAllocationToStopAllocationDTO(allocation);
    }

    private void assertMemberHasAuthority(String memberNameInAllocation, String loggedInMember) {
        if(!memberNameInAllocation.equals(loggedInMember)) throw new IllegalArgumentException("You have no authority to start/stop this allocation.");
    }

    public Allocation getAllocationById(long allocationId) {
        return allocationRepository.findById(allocationId).orElseThrow(() -> new IllegalArgumentException("No allocation exists with the id: " + allocationId));
    }

    private void assertTheAllocationIsActive(Allocation allocation) {
        if (!allocation.getStatus().equals(AllocationStatus.ACTIVE))
            throw new IllegalArgumentException("The allocation is already stopped");
    }

}
