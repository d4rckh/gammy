import { useReportsBy } from "@/lib/api/queries";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "../ui/table";

export function ReportsByPlayer({ playerId }: { playerId: number }) {
  const playerReports = useReportsBy(playerId);

  return playerReports.data && <>
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>For</TableHead>
          <TableHead>Text</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {
          playerReports.data.map(r =>
            <TableRow>
              <TableCell>{r.reportedPlayer?.username}</TableCell>
              <TableCell>{r.text}</TableCell>
            </TableRow>
          )
        }
      </TableBody>
    </Table>
  </>
}