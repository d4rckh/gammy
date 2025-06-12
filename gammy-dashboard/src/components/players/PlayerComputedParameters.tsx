import { usePlayerParameters, usePlayerParametersOverrides, usePlayerStats } from "@/lib/api/queries";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "../ui/table";

export function PlayerComputedParameters({ playerId }: { playerId: number }) {
  const { data } = usePlayerParameters(playerId);

  return data && <>
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>Parameter Path</TableHead>
          <TableHead>Value</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {
          Object.keys(data).map(item =>
            <TableRow>
              <TableCell>{item}</TableCell>
              <TableCell>{data[item]}</TableCell>
            </TableRow>
          )
        }
      </TableBody>
    </Table>
  </>
}