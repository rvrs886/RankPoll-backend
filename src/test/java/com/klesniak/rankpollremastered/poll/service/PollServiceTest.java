package com.klesniak.rankpollremastered.poll.service;

import com.klesniak.rankpollremastered.IntegrationTestBase;
import com.klesniak.rankpollremastered.poll.dto.PollCreationDto;
import com.klesniak.rankpollremastered.poll.dto.SubmitDto;
import com.klesniak.rankpollremastered.poll.entity.AnswerSummary;
import com.klesniak.rankpollremastered.poll.entity.Poll;
import com.klesniak.rankpollremastered.poll.entity.PollSummary;
import com.klesniak.rankpollremastered.poll.entity.SubmitEntry;
import com.klesniak.rankpollremastered.poll.exception.PollAlreadySubmittedException;
import com.klesniak.rankpollremastered.poll.exception.PollNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.klesniak.rankpollremastered.poll.constants.AnswerType.SINGLE_CHOICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PollServiceTest extends IntegrationTestBase {

    @Test
    public void shouldReturnPollByPollId() {
        //given
        Poll poll = createPoll("TestPoll", List.of("answer1", "answer2"), SINGLE_CHOICE);

        //when
        Poll foundPoll = pollService.getPoll(poll.getId());

        //then
        assertThat(foundPoll.getId()).isEqualTo(poll.getId());
    }

    @Test
    public void shouldCorrectlySavePoll() {
        //given
        PollCreationDto pollCreationDto = new PollCreationDto("TestPoll", List.of("answer1", "answer2"), SINGLE_CHOICE);

        //when
        Poll poll = pollService.savePoll(pollCreationDto);

        //then
        assertThat(poll.getTitle()).isEqualTo(pollCreationDto.getTitle());
        assertThat(poll.getAnswers())
                .hasSize(2)
                .contains("answer1", "answer2");
        assertThat(poll.getAnswerType()).isEqualTo(SINGLE_CHOICE);
        assertThat(poll.isClosed()).isFalse();
    }

    @Test
    public void shouldThrowPollNotFoundExceptionWhenPollIsNotPresent() {
        //given
        Poll poll = createPoll("TestPoll", List.of("answer1", "answer2"), SINGLE_CHOICE);
        String notExistingPollId = "notExisting";

        //when & then
        assertThatThrownBy(() -> pollService.getPoll(notExistingPollId))
                .isInstanceOf(PollNotFoundException.class)
                .hasMessage("Could not find poll with id: " + notExistingPollId + "!");
    }

    @Test
    public void shouldCorrectlyUpdateSummary() {
        //given
        Poll poll = createPoll("TestPoll", List.of("answer1", "answer2"), SINGLE_CHOICE);
        SubmitDto submitDto = new SubmitDto(poll.getId(), List.of("answer1"));

        //when
        pollService.submitAnswer(submitDto, "exampleIp");

        //then
        Optional<PollSummary> pollSummary = pollSummaryRepository.findByPollId(poll.getId());
        List<SubmitEntry> submitEntries = submitEntryRepository.findAllByPollId(poll.getId());

        assertThat(pollSummary).isPresent();
        assertThat(submitEntries).hasSize(1);
    }

    @Test
    public void shouldThrowPollAlreadySubmittedExceptionWhenUserVotingFromSameIpAddress() {
        //given
        Poll poll = createPoll("TestPoll", List.of("answer1", "answer2"), SINGLE_CHOICE);
        SubmitDto submitDto1 = new SubmitDto(poll.getId(), List.of("answer1"));
        SubmitDto submitDto2 = new SubmitDto(poll.getId(), List.of("answer2"));
        String ipAddress = "exampleIp";

        //when & then
        pollService.submitAnswer(submitDto1, ipAddress);

        assertThatThrownBy(() -> pollService.submitAnswer(submitDto2, ipAddress))
                .isInstanceOf(PollAlreadySubmittedException.class)
                .hasMessage("Poll with id: " + poll.getId() + " has been already submitted by ip address: [" + ipAddress + "]!");
    }

    @Test
    public void shouldCorrectlyReturnPollSummary() {
        //given
        Poll poll = createPoll("TestPoll", List.of("answer1", "answer2"), SINGLE_CHOICE);
        SubmitDto submitDto1 = new SubmitDto(poll.getId(), List.of("answer1"));
        SubmitDto submitDto2 = new SubmitDto(poll.getId(), List.of("answer1"));
        SubmitDto submitDto3 = new SubmitDto(poll.getId(), List.of("answer2"));
        SubmitDto submitDto4 = new SubmitDto(poll.getId(), List.of("answer3"));
        String exampleIp1 = "exampleIp1";
        String exampleIp2 = "exampleIp2";

        pollService.submitAnswer(submitDto1, exampleIp1);
        pollService.submitAnswer(submitDto2, exampleIp2);

        //when
        PollSummary pollSummary = pollService.getPollSummary(poll.getId());

        //then
        Set<AnswerSummary> answerSummaries = pollSummary.getAnswerSummaries();

        assertThat(answerSummaries).hasSize(1);
    }
}