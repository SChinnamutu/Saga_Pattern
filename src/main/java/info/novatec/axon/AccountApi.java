package info.novatec.axon;

import info.novatec.axon.account.BankAccount;
import info.novatec.axon.account.command.CloseAccountCommand;
import info.novatec.axon.account.command.CreateAccountCommand;
import info.novatec.axon.account.command.DepositMoneyCommand;
import info.novatec.axon.account.command.WithdrawMoneyCommand;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequestMapping("/accounts")
@RestController
public class AccountApi {

	private final CommandGateway commandGateway;

	private final EventStore eventStore;

	public AccountApi(CommandGateway commandGateway, EventStore eventStore) {
		this.commandGateway = commandGateway;
		this.eventStore = eventStore;
	}

	@GetMapping("{id}/events")
	public List<Object> getEvents(@PathVariable String id) {
		return eventStore.readEvents(id).asStream().map(s -> s.getPayload()).collect(Collectors.toList());
	}

	@PostMapping
	public void createAccount(@RequestBody AccountOwner user) {
		String id = UUID.randomUUID().toString();
		commandGateway.send(new CreateAccountCommand(id, user.name), new CommandCallback<CreateAccountCommand, Object>() {

			@Override
			public void onSuccess(CommandMessage<? extends CreateAccountCommand> commandMessage, Object result) {
				System.out.println("Successsfully created");
			}

			@Override
			public void onFailure(CommandMessage<? extends CreateAccountCommand> commandMessage, Throwable cause) {
				System.out.println("Failed to create a account");
			}
		});
	}

	@PutMapping(path = "{accountId}/balance")
	public CompletableFuture<String> deposit(@RequestBody double ammount, @PathVariable String accountId) {
		if (ammount > 0) {
			return commandGateway.send(new DepositMoneyCommand(accountId, ammount));
		} else {
			return commandGateway.send(new WithdrawMoneyCommand(accountId, -ammount));
		}
	}

	@DeleteMapping("{id}")
	public CompletableFuture<String> delete(@PathVariable String id) {
		return commandGateway.send(new CloseAccountCommand(id));
	}

	@ExceptionHandler(AggregateNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void notFound() {
	}

	@ExceptionHandler(BankAccount.InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String insufficientBalance(BankAccount.InsufficientBalanceException exception) {
	    return exception.getMessage();
    }

	static class AccountOwner {
		public String name;
	}
}
