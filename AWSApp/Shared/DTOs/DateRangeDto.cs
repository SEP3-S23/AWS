namespace Shared.DTOs;

public class DateRangeDto
{
    public long startDate { get; set; }
    public long endDate { get; set; }

    public DateRangeDto()
    {
        
    }

    public DateRangeDto(long startDate, long endDate)
    {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}