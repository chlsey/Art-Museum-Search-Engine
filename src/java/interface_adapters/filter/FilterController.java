package interface_adapters.filter;

import use_case.filter.*;

public class FilterController {
    private final FilterInputBoundary filterInputBoundary;
    public FilterController(FilterInputBoundary filterInputBoundary) {
        this.filterInputBoundary = filterInputBoundary;
    }

    public void execute(String spec) {
        final FilterInputData filterdata = new FilterInputData(spec);
        filterInputBoundary.execute(filterdata);
    }
}
