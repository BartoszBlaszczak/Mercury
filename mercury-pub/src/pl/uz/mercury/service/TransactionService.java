package pl.uz.mercury.service;

import java.util.List;

import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.dto.TransactionDto;
import pl.uz.mercury.service.common.MercuryService;

public interface TransactionService
	extends MercuryService <TransactionDto>
{
	List<MerchandiseDto> getMerchandiseDtos();
}
