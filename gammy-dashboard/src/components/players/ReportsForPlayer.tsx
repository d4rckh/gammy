import {useReportsFor} from "@/lib/api/queries";
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "../ui/table";

export function ReportsForPlayer({playerId}: { playerId: number }) {
    const playerReports = useReportsFor(playerId);

    return playerReports.data && <>
        <Table>
            <TableHeader>
                <TableRow>
                    <TableHead>By</TableHead>
                    <TableHead>Text</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {
                    playerReports.data.map(r =>
                        <TableRow>
                            <TableCell>{r.reporterPlayer?.username}</TableCell>
                            <TableCell>{r.text}</TableCell>
                        </TableRow>
                    )
                }
            </TableBody>
        </Table>
    </>
}