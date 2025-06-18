import {usePlayerReports} from "@/lib/api/queries"
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "./ui/table";

export default function PlayerReportsList() {
    const reports = usePlayerReports();

    return reports.data && <>
        <Table>
            <TableHeader>
                <TableRow>
                    <TableHead>ID</TableHead>
                    <TableHead>Reported By</TableHead>
                    <TableHead>Reported Player</TableHead>
                    <TableHead>Text</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {
                    reports.data.map(report =>
                        <TableRow>
                            <TableCell>{report.id}</TableCell>
                            <TableCell>{report.reportedPlayer?.username}</TableCell>
                            <TableCell>{report.reportedPlayer?.username}</TableCell>
                            <TableCell>{report.text}</TableCell>
                        </TableRow>
                    )
                }
            </TableBody>
        </Table>
    </>
}