package be.stijnvanbever.countryreporting.reporting.informationblock;

import be.stijnvanbever.countryreporting.reporting.Report;

import java.util.LinkedList;
import java.util.List;

public class InformationBlockComposite implements InformationBlock {
    private final List<InformationBlock> informationBlocks;
    private final String header;

    public InformationBlockComposite(String header) {
        this.informationBlocks = new LinkedList<>();
        this.header = header;
    }

    public InformationBlockComposite(String header, List<InformationBlock> informationBlocks) {
        this.informationBlocks = informationBlocks;
        this.header = header;
    }

    public List<InformationBlock> getInformationBlocks() {
        return informationBlocks;
    }

    public void addInformationBlock(InformationBlock informationBlock) {
        this.informationBlocks.add(informationBlock);
    }

    @Override
    public String getHeader() {
        return this.header;
    }

    @Override
    public void accept(Report report) {
        report.visit(this);
    }
}
