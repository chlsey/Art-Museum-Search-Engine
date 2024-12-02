package interface_adapters.filter;

import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInputData;

/**
 * Filter controller class.
 */
public class FilterController {
    private final FilterInputBoundary filterInputBoundary;
    public FilterController(FilterInputBoundary filterInputBoundary) {
        this.filterInputBoundary = filterInputBoundary;
    }

    /**
     * Execute.
     * @param spec spec
     */
    public void execute(String spec) {
        final FilterInputData filterdata = new FilterInputData(spec);
        filterInputBoundary.execute(filterdata);
    }
}
