import { usePlayerReports, usePlayerStatsHistory } from "@/lib/api/queries"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "../ui/table";

export default function PlayerStatHistory({ playerId }: { playerId: number }) {
  const history = usePlayerStatsHistory(playerId);

  return history.data && <>
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>ID</TableHead>
          <TableHead>Teimstamp</TableHead>
          <TableHead>Stat</TableHead>
          <TableHead>Old Value -&gt; New Value</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {
          history.data.map(entry =>
            <TableRow>
              <TableCell>{entry.id}</TableCell>
              <TableCell>{entry.timestamp}</TableCell>
              <TableCell>{entry.gameStat.displayName}</TableCell>
              <TableCell>{entry.oldValue} -&gt; {entry.newValue}</TableCell>
            </TableRow>
          )
        }
      </TableBody>
    </Table>
  </>
}